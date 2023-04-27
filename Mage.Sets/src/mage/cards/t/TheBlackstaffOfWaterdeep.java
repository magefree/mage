package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheBlackstaffOfWaterdeep extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledArtifactPermanent("another nontoken artifact you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public TheBlackstaffOfWaterdeep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{U}");

        this.addSuperType(SuperType.LEGENDARY);

        // You many choose not to untap The Blackstaff of Waterdeep during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());

        // Animate Walking Statue — {1}{U}, {T}: Another target nontoken artifact you control becomes a 4/4 artifact creature for as long as The Blackstaff of Waterdeep remains tapped. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new TheBlackstaffOfWaterdeepEffect(), new ManaCostsImpl<>("{1}{U}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability.withFlavorWord("Animate Walking Statue"));
    }

    private TheBlackstaffOfWaterdeep(final TheBlackstaffOfWaterdeep card) {
        super(card);
    }

    @Override
    public TheBlackstaffOfWaterdeep copy() {
        return new TheBlackstaffOfWaterdeep(this);
    }
}

class TheBlackstaffOfWaterdeepEffect extends ContinuousEffectImpl {

    TheBlackstaffOfWaterdeepEffect() {
        super(Duration.Custom, Outcome.Benefit);
        staticText = "another target nontoken artifact you control becomes " +
                "a 4/4 artifact creature for as long as {this} remains tapped";
    }

    private TheBlackstaffOfWaterdeepEffect(final TheBlackstaffOfWaterdeepEffect effect) {
        super(effect);
    }

    @Override
    public TheBlackstaffOfWaterdeepEffect copy() {
        return new TheBlackstaffOfWaterdeepEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        Permanent artifact = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null || !permanent.isTapped() || artifact == null) {
            discard();
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                artifact.addCardType(game, CardType.ARTIFACT);
                artifact.addCardType(game, CardType.CREATURE);
                return true;
            case PTChangingEffects_7:
                if (sublayer == SubLayer.SetPT_7b) {
                    artifact.getPower().setModifiedBaseValue(4);
                    artifact.getToughness().setModifiedBaseValue(4);
                    return true;
                }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4 || layer == Layer.PTChangingEffects_7;
    }
}
