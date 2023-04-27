package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayTheTopCardEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LilaHospitalityHostess extends CardImpl {

    private static final FilterCard filter = new FilterCard("cast common spells");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent(SubType.GUEST, "Guests");

    static {
        filter.add(LilaHospitalityHostessPredicate.instance);
    }

    public LilaHospitalityHostess(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.EMPLOYEE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // You may cast common spells from the top of your library.
        this.addAbility(new SimpleStaticAbility(new PlayTheTopCardEffect(
                TargetController.YOU, filter, false
        )));

        // Guests you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter2
        )));
    }

    private LilaHospitalityHostess(final LilaHospitalityHostess card) {
        super(card);
    }

    @Override
    public LilaHospitalityHostess copy() {
        return new LilaHospitalityHostess(this);
    }
}

enum LilaHospitalityHostessPredicate implements Predicate<Card> {
    instance;

    @Override
    public boolean apply(Card input, Game game) {
        return input.getRarity() == Rarity.COMMON;
    }
}
