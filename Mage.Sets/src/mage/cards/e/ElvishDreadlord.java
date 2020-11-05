package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.EncoreAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElvishDreadlord extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Elf creatures");

    static {
        filter.add(Predicates.not(SubType.ELF.getPredicate()));
    }

    public ElvishDreadlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.ELF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Elvish Dreadlord dies, non-Elf creatures get -3/-3 until end of turn.
        this.addAbility(new DiesSourceTriggeredAbility(new BoostAllEffect(
                -3, -3, Duration.EndOfTurn, filter, false
        )));

        // Encore {5}{B}{B}
        this.addAbility(new EncoreAbility(new ManaCostsImpl<>("{5}{B}{B}")));
    }

    private ElvishDreadlord(final ElvishDreadlord card) {
        super(card);
    }

    @Override
    public ElvishDreadlord copy() {
        return new ElvishDreadlord(this);
    }
}
