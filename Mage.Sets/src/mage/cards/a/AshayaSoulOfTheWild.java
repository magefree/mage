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
import mage.players.Player;

import java.util.List;
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
        Player you = game.getPlayer(source.getControllerId());
        List<Permanent> creatures = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game);
        if (you != null) {
            for (Permanent creature : creatures) {
                if (creature != null) {
                    switch (layer) {
                        case TypeChangingEffects_4:
                            creature.addCardType(CardType.LAND);
                            creature.getSubtype(game).add(SubType.FOREST);
                            break;
                        case AbilityAddingRemovingEffects_6:
                            boolean flag = false;
                            for (Ability ability : creature.getAbilities(game)) {
                                if (ability instanceof GreenManaAbility) {
                                    flag = true;
                                    break;
                                }
                            }
                            if (!flag) {
                                creature.addAbility(new GreenManaAbility(), source.getSourceId(), game);
                            }
                            break;
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4 || layer == Layer.AbilityAddingRemovingEffects_6;
    }
}
