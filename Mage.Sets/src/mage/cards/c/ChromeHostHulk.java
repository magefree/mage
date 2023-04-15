package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChromeHostHulk extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("other target creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ChromeHostHulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.TROLL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.color.setBlue(true);
        this.color.setGreen(true);
        this.nightCard = true;

        // Whenever Chrome Host Hulk attacks, up to one other target creature has base power and toughness 5/5 until end of turn.
        Ability ability = new AttacksTriggeredAbility(
                new SetBasePowerToughnessTargetEffect(5, 5, Duration.EndOfTurn)
        );
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);
    }

    private ChromeHostHulk(final ChromeHostHulk card) {
        super(card);
    }

    @Override
    public ChromeHostHulk copy() {
        return new ChromeHostHulk(this);
    }
}
