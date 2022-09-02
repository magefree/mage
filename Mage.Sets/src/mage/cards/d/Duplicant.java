package mage.cards.d;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.List;
import java.util.UUID;

/**
 * @author Plopman
 */
public final class Duplicant extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken creature");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public Duplicant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Imprint - When Duplicant enters the battlefield, you may exile target nontoken creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DuplicantExileTargetEffect(), true);
        ability.addTarget(new TargetCreaturePermanent(filter));
        ability.setAbilityWord(AbilityWord.IMPRINT);
        this.addAbility(ability);
        // As long as the exiled card is a creature card, Duplicant has that card's power, toughness, and creature types. It's still a Shapeshifter.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DuplicantContinuousEffect()));
    }

    private Duplicant(final Duplicant card) {
        super(card);
    }

    @Override
    public Duplicant copy() {
        return new Duplicant(this);
    }
}

class DuplicantExileTargetEffect extends OneShotEffect {

    public DuplicantExileTargetEffect() {
        super(Outcome.Exile);
    }

    public DuplicantExileTargetEffect(final DuplicantExileTargetEffect effect) {
        super(effect);
    }

    @Override
    public DuplicantExileTargetEffect copy() {
        return new DuplicantExileTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (permanent != null && sourceObject instanceof Permanent) {
            if (permanent.moveToExile(null, null, source, game)
                    && ((Permanent) sourceObject).imprint(permanent.getId(), game)) {
                ((Permanent) sourceObject).addInfo("imprint", "[Imprinted card - " + permanent.getName() + ']', game);
            }
            return true;
        }

        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "you may exile target nontoken creature";
    }
}

class DuplicantContinuousEffect extends ContinuousEffectImpl {

    public DuplicantContinuousEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "As long as a card exiled with {this} is a creature card, {this} has the power, toughness, and creature types of the last creature card exiled with {this}. It's still a Shapeshifter.";
    }

    public DuplicantContinuousEffect(final DuplicantContinuousEffect effect) {
        super(effect);
    }

    @Override
    public DuplicantContinuousEffect copy() {
        return new DuplicantContinuousEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        if (permanent.getImprinted().isEmpty()) {
            return false;
        }
        List<UUID> imprinted = permanent.getImprinted();
        if (imprinted == null || imprinted.isEmpty()) {
            return false;
        }
        Card card = game.getCard(imprinted.get(imprinted.size() - 1));
        if (card == null || !card.isCreature(game)) {
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                permanent.copySubTypesFrom(game, card, SubTypeSet.CreatureType);
                break;
            case PTChangingEffects_7:
                if (sublayer == SubLayer.SetPT_7b) {
                    permanent.getPower().setModifiedBaseValue(card.getPower().getValue());
                    permanent.getToughness().setModifiedBaseValue(card.getToughness().getValue());
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
