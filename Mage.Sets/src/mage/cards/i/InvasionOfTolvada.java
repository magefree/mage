package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfTolvada extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("nonbattle permanent card from your graveyard");

    static {
        filter.add(Predicates.not(CardType.BATTLE.getPredicate()));
    }

    public InvasionOfTolvada(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.BATTLE}, "{3}{W}{B}");

        this.subtype.add(SubType.SIEGE);
        this.setStartingDefense(5);
        this.secondSideCardClazz = mage.cards.t.TheBrokenSky.class;

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.addAbility(new SiegeAbility());

        // When Invasion of Tolvada enters the battlefield, return target nonbattle permanent card from your graveyard to the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private InvasionOfTolvada(final InvasionOfTolvada card) {
        super(card);
    }

    @Override
    public InvasionOfTolvada copy() {
        return new InvasionOfTolvada(this);
    }
}
