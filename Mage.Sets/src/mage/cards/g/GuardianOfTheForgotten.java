package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.keyword.ManifestEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.ModifiedPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GuardianOfTheForgotten extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("a modified creature you control");

    static {
        filter.add(ModifiedPredicate.instance);
    }

    public GuardianOfTheForgotten(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever a modified creature you control dies, manifest the top card of your library.
        this.addAbility(new DiesCreatureTriggeredAbility(new ManifestEffect(1), false, filter));
    }

    private GuardianOfTheForgotten(final GuardianOfTheForgotten card) {
        super(card);
    }

    @Override
    public GuardianOfTheForgotten copy() {
        return new GuardianOfTheForgotten(this);
    }
}
