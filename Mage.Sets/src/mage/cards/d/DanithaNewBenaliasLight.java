package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.CastFromGraveyardOnceEachTurnAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DanithaNewBenaliasLight extends CardImpl {

    private static final FilterCard filter = new FilterCard("an Aura or Equipment spell");
    static {
        filter.add(Predicates.or(
                SubType.AURA.getPredicate(), SubType.EQUIPMENT.getPredicate()
        ));
    }

    public DanithaNewBenaliasLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Once during each of your turns, you may cast an Aura or Equipment spell from your graveyard.
        this.addAbility(new CastFromGraveyardOnceEachTurnAbility(filter));
    }

    private DanithaNewBenaliasLight(final DanithaNewBenaliasLight card) {
        super(card);
    }

    @Override
    public DanithaNewBenaliasLight copy() {
        return new DanithaNewBenaliasLight(this);
    }
}
