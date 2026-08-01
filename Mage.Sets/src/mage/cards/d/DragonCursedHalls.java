package mage.cards.d;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class DragonCursedHalls extends CardImpl {

    public DragonCursedHalls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {T}: Until end of turn, target creature gains "Whenever this creature deals combat damage to a player, create a Treasure token."
        Ability gainedAbility = new DealsCombatDamageToAPlayerTriggeredAbility(new CreateTokenEffect(new TreasureToken()));
        Ability ability = new SimpleActivatedAbility(
            new GainAbilityTargetEffect(gainedAbility)
                .setText("Until end of turn, target creature gains \"" + gainedAbility.getRule() + "\""),
            new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private DragonCursedHalls(final DragonCursedHalls card) {
        super(card);
    }

    @Override
    public DragonCursedHalls copy() {
        return new DragonCursedHalls(this);
    }
}
