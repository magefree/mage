package mage.cards.m;

import mage.MageInt;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.PackTacticsAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MinionOfTheMighty extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("a Dragon creature card");

    static {
        filter.add(SubType.DRAGON.getPredicate());
    }

    public MinionOfTheMighty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.KOBOLD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Pack tactics — Whenever Minion of the Mighty attacks, if you attacked with creatures wih total power 6 or greater this combat, you may put a Dragon creature card from your hand onto the battlefield tapped and attacking.
        this.addAbility(new PackTacticsAbility(new PutCardFromHandOntoBattlefieldEffect(
                filter, false, true, true
        )));
    }

    private MinionOfTheMighty(final MinionOfTheMighty card) {
        super(card);
    }

    @Override
    public MinionOfTheMighty copy() {
        return new MinionOfTheMighty(this);
    }
}
