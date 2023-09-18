package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PermanentsEnterBattlefieldTappedEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ThaliaHereticCathar extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("creatures and nonbasic lands your opponents control");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                Predicates.and(
                        Predicates.not(SuperType.BASIC.getPredicate()),
                        CardType.LAND.getPredicate()
                )
        ));
    }

    public ThaliaHereticCathar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Creatures and nonbasic lands your opponents control enter the battlefield tapped.
        this.addAbility(new SimpleStaticAbility(new PermanentsEnterBattlefieldTappedEffect(filter)));
    }

    private ThaliaHereticCathar(final ThaliaHereticCathar card) {
        super(card);
    }

    @Override
    public ThaliaHereticCathar copy() {
        return new ThaliaHereticCathar(this);
    }
}
