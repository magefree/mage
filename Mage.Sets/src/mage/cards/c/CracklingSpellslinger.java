package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.NextSpellCastHasAbilityEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.StormAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CracklingSpellslinger extends CardImpl {

    private static final FilterCard filter = new FilterInstantOrSorceryCard("instant or sorcery spell");

    public CracklingSpellslinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Crackling Spellslinger enters the battlefield, if you cast it, the next instant or sorcery spell you cast this turn has storm.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new NextSpellCastHasAbilityEffect(new StormAbility(), filter)),
                CastFromEverywhereSourceCondition.instance,
                "When {this} enters the battlefield, if you cast it, "
                        + "the next instant or sorcery spell you cast this turn has storm"
        ));
    }

    private CracklingSpellslinger(final CracklingSpellslinger card) {
        super(card);
    }

    @Override
    public CracklingSpellslinger copy() {
        return new CracklingSpellslinger(this);
    }
}
