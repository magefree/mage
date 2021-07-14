package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Loki
 */
public final class CoreProwler extends CardImpl {

    public CoreProwler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Infect (This creature deals damage to creatures in the form of -1/-1 counters and to players in the form of poison counters.)
        this.addAbility(InfectAbility.getInstance());

        // When Core Prowler dies, proliferate. (You choose any number of permanents and/or players with counters on them, then give each another counter of a kind already there.)
        this.addAbility(new DiesSourceTriggeredAbility(new ProliferateEffect()));
    }

    private CoreProwler(final CoreProwler card) {
        super(card);
    }

    @Override
    public CoreProwler copy() {
        return new CoreProwler(this);
    }

}
