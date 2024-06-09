package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.EachSpellYouCastHasReplicateEffect;
import mage.abilities.keyword.DoctorsCompanionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IanChesterton extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("Saga spell");

    static {
        filter.add(SubType.SAGA.getPredicate());
    }

    public IanChesterton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Science Teacher -- Each Saga spell you cast has replicate. The replicate cost is equal to its mana cost.
        this.addAbility(new SimpleStaticAbility(new EachSpellYouCastHasReplicateEffect(
                filter, "When you cast that Saga, copy it for each time you paid " +
                "its replicate cost. Copies of permanents enter the battlefield as tokens."
        )).withFlavorWord("Science Teacher"));

        // Doctor's companion
        this.addAbility(DoctorsCompanionAbility.getInstance());
    }

    private IanChesterton(final IanChesterton card) {
        super(card);
    }

    @Override
    public IanChesterton copy() {
        return new IanChesterton(this);
    }
}
