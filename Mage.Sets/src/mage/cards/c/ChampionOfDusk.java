package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author L_J
 */
public final class ChampionOfDusk extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Vampires you control");

    static {
        filter.add(SubType.VAMPIRE.getPredicate());
    }

    private static final DynamicValue xCount = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Vampires you control", xCount);

    public ChampionOfDusk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Champion of Dusk enters the battlefield, you draw X cards and you lose X life, where X is the number of Vampires you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DrawCardSourceControllerEffect(xCount).setText("you draw X cards")
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(xCount)
                .setText("and you lose X life, where X is the number of Vampires you control"));
        this.addAbility(ability.addHint(hint));
    }

    private ChampionOfDusk(final ChampionOfDusk card) {
        super(card);
    }

    @Override
    public ChampionOfDusk copy() {
        return new ChampionOfDusk(this);
    }
}
