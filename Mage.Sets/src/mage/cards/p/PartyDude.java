package mage.cards.p;

import java.util.UUID;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetAttackingCreature;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.common.CreateTokenAllEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainClassAbilitySourceEffect;
import mage.abilities.keyword.ClassLevelAbility;
import mage.abilities.keyword.ClassReminderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class PartyDude extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactPermanent("an artifact an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public PartyDude(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        this.subtype.add(SubType.CLASS);

        // (Gain the next level as a sorcery to add its ability.)
        this.addAbility(new ClassReminderAbility());

        // When this Class enters, each player creates a Food token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenAllEffect(new FoodToken(), TargetController.EACH_PLAYER)));

        // {1}{G}: Level 2
        this.addAbility(new ClassLevelAbility(2, "{1}{G}"));

        // Whenever an artifact an opponent controls is put into a graveyard from the battlefield, draw a card.
        this.addAbility(new SimpleStaticAbility(
            new GainClassAbilitySourceEffect(
                new PutIntoGraveFromBattlefieldAllTriggeredAbility(
                    new DrawCardSourceControllerEffect(1), false, filter, false
                ), 2
            )
        ));

        // {4}{G}: Level 3
        this.addAbility(new ClassLevelAbility(3, "{4}{G}"));

        // Whenever one or more of your opponents are attacked, up to one target attacking creature gets +X/+X until end of turn, where X is the number of cards in your hand.
        Ability ability = new PartyDudeTriggeredAbility();
        ability.addTarget(new TargetAttackingCreature(0, 1));
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(ability, 3)));
    }

    private PartyDude(final PartyDude card) {
        super(card);
    }

    @Override
    public PartyDude copy() {
        return new PartyDude(this);
    }
}

class PartyDudeTriggeredAbility extends TriggeredAbilityImpl {
    PartyDudeTriggeredAbility() {
        super(
            Zone.BATTLEFIELD,
            new BoostTargetEffect(CardsInControllerHandCount.ANY, CardsInControllerHandCount.ANY)
                .setText("up to one target attacking creature gets +X/+X until end of turn, where X is the number of cards in your hand"),
            false
        );
        this.setTriggerPhrase("Whenever one or more of your opponents are attacked, ");
    }

    private PartyDudeTriggeredAbility(final PartyDudeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PartyDudeTriggeredAbility copy() {
        return new PartyDudeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DEFENDER_ATTACKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!game.getOpponents(this.getControllerId()).contains(event.getTargetId())) {
            return false;
        }
        return true;
    }
}
