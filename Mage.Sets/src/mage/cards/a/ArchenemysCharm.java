package mage.cards.a;

import mage.abilities.Mode;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArchenemysCharm extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature and/or planeswalker cards from your graveyard");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    public ArchenemysCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}{B}{B}");

        // Choose one --
        // * Exile target creature or planeswalker.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());

        // * Return one or two target creature and/or planeswalker cards from your graveyard to your hand.
        this.getSpellAbility().addMode(new Mode(new ReturnFromGraveyardToHandTargetEffect())
                .addTarget(new TargetCardInYourGraveyard(1, 2, filter)));

        // * Put two +1/+1 counters on target creature you control. It gains lifelink until end of turn.
        this.getSpellAbility().addMode(new Mode(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)))
                .addEffect(new GainAbilityTargetEffect(LifelinkAbility.getInstance())
                        .setText("It gains lifelink until end of turn"))
                .addTarget(new TargetControlledCreaturePermanent()));
    }

    private ArchenemysCharm(final ArchenemysCharm card) {
        super(card);
    }

    @Override
    public ArchenemysCharm copy() {
        return new ArchenemysCharm(this);
    }
}
