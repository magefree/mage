package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author jmharmon
 */

public final class AshayaSoulOfTheWild extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent("lands you control");

    public AshayaSoulOfTheWild(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Ashaya, Soul of the Wild’s power and toughness are each equal to the number of lands you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(filter), Duration.EndOfGame)));

        // Nontoken creatures you control are Forest lands in addition to their other types. (They’re still affected by summoning sickness.)
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AshayaSoulOfTheWildEffect()));
    }

    public AshayaSoulOfTheWild(final AshayaSoulOfTheWild card) {
        super(card);
    }

    @Override
    public AshayaSoulOfTheWild copy() {
        return new AshayaSoulOfTheWild(this);
    }
}

class AshayaSoulOfTheWildEffect extends ContinuousEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken creatures");

    static {
        filter.add(Predicates.not(TokenPredicate.instance));
    }

    public AshayaSoulOfTheWildEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "Nontoken creatures you control are Forest lands in addition to their other types";
        this.dependencyTypes.add(DependencyType.BecomeForest);
    }

    public AshayaSoulOfTheWildEffect(final AshayaSoulOfTheWildEffect effect) {
        super(effect);
    }

    @Override
    public AshayaSoulOfTheWildEffect copy() {
        return new AshayaSoulOfTheWildEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer subLayer, Ability source, Game game) {
        for (Permanent land : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
            switch (layer) {
                case TypeChangingEffects_4:
                    // land abilities are intrinsic, so add them here, not in layer 6
                    if (!land.hasSubtype(SubType.FOREST, game)) {
                        land.getSubtype(game).add(SubType.FOREST);
                        if (!land.getAbilities(game).containsClass(GreenManaAbility.class)) {
                            land.addAbility(new GreenManaAbility(), source.getSourceId(), game);
                        }
                    }
                    land.addCardType(CardType.LAND);
                    break;
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
        return layer == Layer.TypeChangingEffects_4;
    }
}
