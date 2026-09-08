package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.common.ScryOrSurveilTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPermanent;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class DivinerOfVictory extends PrepareCard {

    private static final FilterPermanent filter = new FilterOpponentsCreaturePermanent("creature an opponent controls with mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 3));
    }

    public DivinerOfVictory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}", "Unwind History", new CardType[]{CardType.INSTANT}, "{1}{U}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // Whenever you scry or surveil, this creature gets +1/+1 until end of turn.
        this.addAbility(new ScryOrSurveilTriggeredAbility(
            new BoostSourceEffect(1, 1, Duration.EndOfTurn)
        ));

        // Unwind History
        // Sorcery {1}{U}
        // Return target creature an opponent controls with mana value 3 or less to its owner's hand. Surveil 1.
        this.getSpellCard().getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellCard().getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellCard().getSpellAbility().addEffect(new SurveilEffect(1));
    }

    private DivinerOfVictory(final DivinerOfVictory card) {
        super(card);
    }

    @Override
    public DivinerOfVictory copy() {
        return new DivinerOfVictory(this);
    }
}
