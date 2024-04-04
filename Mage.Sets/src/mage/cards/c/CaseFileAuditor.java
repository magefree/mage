package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.meta.OrTriggeredAbility;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterEnchantmentCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.ManaPoolItem;
import mage.util.CardUtil;

/**
 *
 * @author DominionSpy
 */
public final class CaseFileAuditor extends CardImpl {

    private static final FilterCard filter = new FilterEnchantmentCard();

    public CaseFileAuditor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // When Case File Auditor enters the battlefield and whenever you solve a Case, look at the top six cards of your library.
        // You may reveal an enchantment card from among them and put it into your hand.
        // Put the rest on the bottom of your library in a random order.
        this.addAbility(new OrTriggeredAbility(Zone.BATTLEFIELD,
                new LookLibraryAndPickControllerEffect(6, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM),
                false, "When {this} enters the battlefield and whenever you solve a Case, ",
                new EntersBattlefieldTriggeredAbility(null),
                new CaseFileAuditorTriggeredAbility()));

        // You may spend mana as though it were mana of any color to cast Case spells.
        this.addAbility(new SimpleStaticAbility(new CaseFileAuditorManaEffect()));
    }

    private CaseFileAuditor(final CaseFileAuditor card) {
        super(card);
    }

    @Override
    public CaseFileAuditor copy() {
        return new CaseFileAuditor(this);
    }
}

class CaseFileAuditorTriggeredAbility extends TriggeredAbilityImpl {

    CaseFileAuditorTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
        this.setTriggerPhrase("Whenever you solve a Case, ");
    }

    private CaseFileAuditorTriggeredAbility(final CaseFileAuditorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CaseFileAuditorTriggeredAbility copy() {
        return new CaseFileAuditorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CASE_SOLVED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId());
    }
}

class CaseFileAuditorManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    CaseFileAuditorManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "you may spend mana as though it were mana of any color to cast Case spells";
    }

    private CaseFileAuditorManaEffect(final CaseFileAuditorManaEffect effect) {
        super(effect);
    }

    @Override
    public CaseFileAuditorManaEffect copy() {
        return new CaseFileAuditorManaEffect(this);
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!source.isControlledBy(affectedControllerId)) {
            return false;
        }
        MageObject mageObject = game.getObject(CardUtil.getMainCardId(game, objectId));
        return mageObject != null && mageObject.hasSubtype(SubType.CASE, game);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
}
