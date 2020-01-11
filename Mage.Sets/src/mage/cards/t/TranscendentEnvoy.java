package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TranscendentEnvoy extends CardImpl {

    private static final FilterCard filter = new FilterCard("Aura spells");

    static {
        filter.add(SubType.AURA.getPredicate());
    }

    public TranscendentEnvoy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.GRIFFIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Aura spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));
    }

    private TranscendentEnvoy(final TranscendentEnvoy card) {
        super(card);
    }

    @Override
    public TranscendentEnvoy copy() {
        return new TranscendentEnvoy(this);
    }
}
