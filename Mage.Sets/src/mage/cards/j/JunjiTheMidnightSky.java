package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JunjiTheMidnightSky extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("non-Dragon creature card from a graveyard");

    static {
        filter.add(Predicates.not(SubType.DRAGON.getPredicate()));
    }

    public JunjiTheMidnightSky(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility(false));

        // When Junji, the Midnight Sky dies, choose one —
        // • Each opponent discards two cards and loses 2 life.
        Ability ability = new DiesSourceTriggeredAbility(new DiscardEachPlayerEffect(
                StaticValue.get(2), false, TargetController.OPPONENT
        ));
        ability.addEffect(new LoseLifeOpponentsEffect(2).setText("and loses 2 life"));

        // • Put target non-Dragon creature card from a graveyard onto the battlefield under your control. You lose 2 life.
        Mode mode = new Mode(new ReturnFromGraveyardToBattlefieldTargetEffect());
        mode.addEffect(new LoseLifeSourceControllerEffect(2));
        mode.addTarget(new TargetCardInGraveyard(filter));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private JunjiTheMidnightSky(final JunjiTheMidnightSky card) {
        super(card);
    }

    @Override
    public JunjiTheMidnightSky copy() {
        return new JunjiTheMidnightSky(this);
    }
}
