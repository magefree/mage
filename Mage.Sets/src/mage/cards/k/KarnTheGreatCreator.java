package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.WishEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KarnTheGreatCreator extends CardImpl {

    private static final FilterPermanent filter
            = new FilterArtifactPermanent("noncreature artifact");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    public KarnTheGreatCreator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.KARN);
        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(5));

        // Activated abilities of artifacts your opponents control can't be activated.
        this.addAbility(new SimpleStaticAbility(new KarnTheGreatCreatorCantActivateEffect()));

        // +1: Until your next turn, up to one target noncreature artifact becomes an artifact creature with power and toughness equal to its converted mana cost.
        Ability ability = new LoyaltyAbility(new KarnTheGreatCreatorAnimateEffect(), 1);
        ability.addTarget(new TargetPermanent(0, 1, filter, false));
        this.addAbility(ability);

        // -2: You may choose an artifact card you own from outside the game or in exile, reveal that card, and put it into your hand.
        this.addAbility(new LoyaltyAbility(new WishEffect(
                StaticFilters.FILTER_CARD_ARTIFACT_AN, true, true
        ), -2));
    }

    private KarnTheGreatCreator(final KarnTheGreatCreator card) {
        super(card);
    }

    @Override
    public KarnTheGreatCreator copy() {
        return new KarnTheGreatCreator(this);
    }
}

class KarnTheGreatCreatorCantActivateEffect extends RestrictionEffect {

    KarnTheGreatCreatorCantActivateEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Activated abilities of artifacts your opponents control can't be activated";
    }

    private KarnTheGreatCreatorCantActivateEffect(final KarnTheGreatCreatorCantActivateEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.isArtifact() && game.getOpponents(source.getControllerId()).contains(permanent.getControllerId());
    }

    @Override
    public boolean canUseActivatedAbilities(Permanent permanent, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public KarnTheGreatCreatorCantActivateEffect copy() {
        return new KarnTheGreatCreatorCantActivateEffect(this);
    }
}

class KarnTheGreatCreatorAnimateEffect extends ContinuousEffectImpl {

    KarnTheGreatCreatorAnimateEffect() {
        super(Duration.UntilYourNextTurn, Outcome.BecomeCreature);
        staticText = "Until your next turn, up to one target noncreature artifact becomes " +
                "an artifact creature with power and toughness each equal to its converted mana cost.";
    }

    private KarnTheGreatCreatorAnimateEffect(final KarnTheGreatCreatorAnimateEffect effect) {
        super(effect);
    }

    @Override
    public KarnTheGreatCreatorAnimateEffect copy() {
        return new KarnTheGreatCreatorAnimateEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent artifact = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (artifact == null) {
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                if (sublayer == SubLayer.NA) {
                    if (!artifact.isCreature()) {
                        artifact.addCardType(CardType.CREATURE);
                    }
                }
                break;

            case PTChangingEffects_7:
                if (sublayer == SubLayer.SetPT_7b) {
                    int cmc = artifact.getConvertedManaCost();
                    artifact.getPower().setValue(cmc);
                    artifact.getToughness().setValue(cmc);
                }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }


    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7 || layer == Layer.TypeChangingEffects_4;
    }
}
