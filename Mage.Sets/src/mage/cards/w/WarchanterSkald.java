package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.DwarfBerserkerToken;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WarchanterSkald extends CardImpl {

    public WarchanterSkald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Warchanter Skald becomes tapped, if it's enchanted or equipped, create a 2/1 red Dwarf Berserker creature token.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BecomesTappedSourceTriggeredAbility(new CreateTokenEffect(new DwarfBerserkerToken())),
                WarchanterSkaldCondition.instance, "Whenever {this} becomes tapped, " +
                "if it's enchanted or equipped, create a 2/1 red Dwarf Berserker creature token."
        ));
    }

    private WarchanterSkald(final WarchanterSkald card) {
        super(card);
    }

    @Override
    public WarchanterSkald copy() {
        return new WarchanterSkald(this);
    }
}

enum WarchanterSkaldCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        return permanent != null && permanent
                .getAttachments()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .anyMatch(p -> p.hasSubtype(SubType.AURA, game) || p.hasSubtype(SubType.EQUIPMENT, game));
    }
}
