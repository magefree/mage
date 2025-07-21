package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class WitchHunter extends CardImpl {

    public WitchHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Witch Hunter deals 1 damage to target player.
        Ability damageAbility = new SimpleActivatedAbility(new DamageTargetEffect(1), new TapSourceCost());
        damageAbility.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(damageAbility);

        // {1}{W}{W}, {tap}: Return target creature an opponent controls to its owner's hand.
        Ability returnAbility = new SimpleActivatedAbility(new ReturnToHandTargetEffect(), new ManaCostsImpl<>("{1}{W}{W}"));
        returnAbility.addCost(new TapSourceCost());
        returnAbility.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(returnAbility);
    }

    private WitchHunter(final WitchHunter card) {
        super(card);
    }

    @Override
    public WitchHunter copy() {
        return new WitchHunter(this);
    }
}
