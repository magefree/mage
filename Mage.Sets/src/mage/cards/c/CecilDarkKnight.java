package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DealsDamageSourceTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Controllable;
import mage.game.Game;
import mage.players.Player;

import java.util.Optional;
import java.util.UUID;

/**
 * @author balazskristof
 */
public final class CecilDarkKnight extends TransformingDoubleFacedCard {

    public CecilDarkKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.KNIGHT}, "{B}",
                "Cecil, Redeemed Paladin",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.KNIGHT}, "W");

        // Cecil, Dark Knight
        this.getLeftHalfCard().setPT(2, 3);

        // Deathtouch
        this.getLeftHalfCard().addAbility(DeathtouchAbility.getInstance());

        // Darkness -- Whenever Cecil deals damage, you lose that much life. Then if your life total is less than or equal to half your starting life total, untap Cecil and transform it.
        Ability ability = new DealsDamageSourceTriggeredAbility(new LoseLifeSourceControllerEffect(SavedDamageValue.MUCH), false).withFlavorWord("Darkness");
        ability.addEffect(new ConditionalOneShotEffect(
                new UntapSourceEffect(),
                CecilDarkKnightCondition.instance
        ).addEffect(
                new TransformSourceEffect(true).concatBy("and")
        ).concatBy("Then"));
        this.getLeftHalfCard().addAbility(ability);

        // Cecil, Redeemed Paladin
        this.getRightHalfCard().setPT(4, 4);

        // Lifelink
        this.getRightHalfCard().addAbility(LifelinkAbility.getInstance());

        // Protect -- Whenever Cecil attacks, other attacking creatures gain indestructible until end of turn.
        this.getRightHalfCard().addAbility(new AttacksTriggeredAbility(new GainAbilityAllEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_ATTACKING_CREATURES, true
        )).withFlavorWord("Protect"));
    }

    private CecilDarkKnight(final CecilDarkKnight card) {
        super(card);
    }

    @Override
    public CecilDarkKnight copy() {
        return new CecilDarkKnight(this);
    }
}

enum CecilDarkKnightCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(source)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .map(Player::getLife)
                .map(life -> life <= game.getStartingLife() / 2)
                .orElse(false);
    }

    @Override
    public String toString() {
        return "your life total is less than or equal to half your starting life total";
    }
}
