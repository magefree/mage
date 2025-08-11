package mage.cards.g;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CantBeCounteredControlledEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PreventAllDamageToAllEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.LivingMetalAbility;
import mage.abilities.keyword.MoreThanMeetsTheEyeAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoldbugHumanitysAlly extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent(SubType.HUMAN, "attacking Humans you control");
    private static final FilterSpell filter2 = new FilterSpell("Human spells");

    static {
        filter.add(AttackingPredicate.instance);
        filter2.add(SubType.HUMAN.getPredicate());
    }

    public GoldbugHumanitysAlly(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.ROBOT}, "{1}{W}{U}",
                "Goldbug, Scrappy Scout",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.VEHICLE}, "UW"
        );
        this.getLeftHalfCard().setPT(3, 3);
        this.getRightHalfCard().setPT(1, 3);

        this.secondSideCardClazz = mage.cards.g.GoldbugScrappyScout.class;

        // More Than Meets the Eye {W}{U}
        this.getLeftHalfCard().addAbility(new MoreThanMeetsTheEyeAbility(this, "{W}{U}"));

        // Prevent all combat damage that would be dealt to attacking Humans you control.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new PreventAllDamageToAllEffect(
                Duration.WhileOnBattlefield, filter, true
        )));

        // Whenever you cast your second spell each turn, convert Goldbug.
        this.getLeftHalfCard().addAbility(new CastSecondSpellTriggeredAbility(new TransformSourceEffect().setText("convert {this}")));

        // Goldbug, Scrappy Scout
        // Living metal
        this.getRightHalfCard().addAbility(new LivingMetalAbility());

        // Human spells you control can't be countered.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(
                new CantBeCounteredControlledEffect(filter2, Duration.WhileOnBattlefield)
        ));

        // Whenever Goldbug and at least one Human attack, draw a card and convert Goldbug.
        this.getRightHalfCard().addAbility(new GoldbugScrappyScoutTriggeredAbility());

        this.finalizeDFC();
    }

    private GoldbugHumanitysAlly(final GoldbugHumanitysAlly card) {
        super(card);
    }

    @Override
    public GoldbugHumanitysAlly copy() {
        return new GoldbugHumanitysAlly(this);
    }
}

class GoldbugScrappyScoutTriggeredAbility extends TriggeredAbilityImpl {

    GoldbugScrappyScoutTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        this.addEffect(new TransformSourceEffect());
    }

    private GoldbugScrappyScoutTriggeredAbility(final GoldbugScrappyScoutTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GoldbugScrappyScoutTriggeredAbility copy() {
        return new GoldbugScrappyScoutTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game
                .getCombat()
                .getAttackers()
                .contains(getSourceId())
                && game
                .getCombat()
                .getAttackers()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .anyMatch(permanent -> permanent.hasSubtype(SubType.HUMAN, game));
    }

    @Override
    public String getRule() {
        return "Whenever {this} and at least one Human attack, draw a card and convert {this}.";
    }
}
