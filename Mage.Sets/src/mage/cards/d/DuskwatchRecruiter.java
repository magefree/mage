package mage.cards.d;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;

/**
 * @author fireshoes
 */
public final class DuskwatchRecruiter extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("a creature card");

    public DuskwatchRecruiter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.WEREWOLF);

        this.secondSideCardClazz = mage.cards.k.KrallenhordeHowler.class;

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {2}{G}: Look at the top three cards of your library. You may reveal a creature card from among them and put it into your hand.
        // Put the rest on the bottom of your library in any order.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new LookLibraryAndPickControllerEffect(3, 1, filter, true, false, Zone.HAND, true),
                new ManaCostsImpl("{2}{G}")));

        // At the beginning of each upkeep, if no spells were cast last turn, transform Duskwatch Recruiter.
        this.addAbility(new TransformAbility());
        this.addAbility(new WerewolfFrontTriggeredAbility());
    }

    private DuskwatchRecruiter(final DuskwatchRecruiter card) {
        super(card);
    }

    @Override
    public DuskwatchRecruiter copy() {
        return new DuskwatchRecruiter(this);
    }

}