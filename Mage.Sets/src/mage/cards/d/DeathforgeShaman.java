package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.MultikickerCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.MultikickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class DeathforgeShaman extends CardImpl {

    public DeathforgeShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Multikicker {R}
        this.addAbility(new MultikickerAbility("{R}"));

        // When Deathforge Shaman enters the battlefield, it deals damage to target player equal to twice the number of times it was kicked.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DeathforgeShamanEffect());
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    private DeathforgeShaman(final DeathforgeShaman card) {
        super(card);
    }

    @Override
    public DeathforgeShaman copy() {
        return new DeathforgeShaman(this);
    }
}

class DeathforgeShamanEffect extends OneShotEffect {

    public DeathforgeShamanEffect() {
        super(Outcome.Damage);
        staticText = "it deals damage to target player or planeswalker equal to twice the number of times it was kicked";
    }

    private DeathforgeShamanEffect(final DeathforgeShamanEffect effect) {
        super(effect);
    }

    @Override
    public DeathforgeShamanEffect copy() {
        return new DeathforgeShamanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int damage = MultikickerCount.instance.calculate(game, source, this) * 2;
        return new DamageTargetEffect(damage).apply(game, source);
    }

}
