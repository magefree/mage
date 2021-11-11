package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PermanentsEnterBattlefieldTappedEffect;
import mage.abilities.effects.common.continuous.CantCastMoreThanOneSpellEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArchonOfEmeria extends CardImpl {

    private static final FilterPermanent filter = new FilterLandPermanent("nonbasic lands your opponents control");

    static {
        filter.add(Predicates.not(SuperType.BASIC.getPredicate()));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public ArchonOfEmeria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.ARCHON);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Each player can't cast more than one spell each turn.
        this.addAbility(new SimpleStaticAbility(new CantCastMoreThanOneSpellEffect(TargetController.ANY)));

        // Nonbasic lands your opponents control enter the battlefield tapped.
        this.addAbility(new SimpleStaticAbility(new PermanentsEnterBattlefieldTappedEffect(filter)));
    }

    private ArchonOfEmeria(final ArchonOfEmeria card) {
        super(card);
    }

    @Override
    public ArchonOfEmeria copy() {
        return new ArchonOfEmeria(this);
    }
}
