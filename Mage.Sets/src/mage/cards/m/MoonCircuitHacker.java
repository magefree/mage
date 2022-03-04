package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoonCircuitHacker extends CardImpl {

    public MoonCircuitHacker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Ninjutsu {U}
        this.addAbility(new NinjutsuAbility("{U}"));

        // Whenever Moon-Circuit Hacker deals combat damage to a player, you may draw a card. If you do, discard a card unless Moon-Circuit Hacker entered the battlefield this turn.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), true
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new DiscardControllerEffect(1), MoonCircuitHackerCondition.instance,
                "If you do, discard a card unless {this} entered the battlefield this turn"
        ));
        this.addAbility(ability);
    }

    private MoonCircuitHacker(final MoonCircuitHacker card) {
        super(card);
    }

    @Override
    public MoonCircuitHacker copy() {
        return new MoonCircuitHacker(this);
    }
}

enum MoonCircuitHackerCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        return permanent == null || permanent.getTurnsOnBattlefield() > 0;
    }
}