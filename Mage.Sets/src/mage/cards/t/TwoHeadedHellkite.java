package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TwoHeadedHellkite extends CardImpl {

    public TwoHeadedHellkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}{B}{R}{G}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Two-Headed Hellkite attacks, draw two cards.
        this.addAbility(new AttacksTriggeredAbility(new DrawCardSourceControllerEffect(2)));
    }

    private TwoHeadedHellkite(final TwoHeadedHellkite card) {
        super(card);
    }

    @Override
    public TwoHeadedHellkite copy() {
        return new TwoHeadedHellkite(this);
    }
}
