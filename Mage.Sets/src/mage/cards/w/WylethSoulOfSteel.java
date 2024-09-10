package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WylethSoulOfSteel extends CardImpl {

    public WylethSoulOfSteel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Wyleth, Soul of Steel attacks, draw a card for each Aura and Equipment attached to it.
        this.addAbility(new AttacksTriggeredAbility(new DrawCardSourceControllerEffect(WylethSoulOfSteelValue.instance), false));
    }

    private WylethSoulOfSteel(final WylethSoulOfSteel card) {
        super(card);
    }

    @Override
    public WylethSoulOfSteel copy() {
        return new WylethSoulOfSteel(this);
    }
}

enum WylethSoulOfSteelValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent permanent = sourceAbility.getSourcePermanentOrLKI(game);
        if (permanent == null) {
            return 0;
        }
        return permanent
                .getAttachments()
                .stream()
                .map(game::getPermanentOrLKIBattlefield)
                .filter(Objects::nonNull)
                .map(p -> p.hasSubtype(SubType.EQUIPMENT, game) || p.hasSubtype(SubType.AURA, game))
                .mapToInt(b -> b ? 1 : 0)
                .sum();
    }

    @Override
    public WylethSoulOfSteelValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "Aura and Equipment attached to it";
    }

    @Override
    public String toString() {
        return "1";
    }
}