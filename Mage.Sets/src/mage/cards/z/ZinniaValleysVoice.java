package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.OffspringAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.BasePowerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author brahle
 */
public final class ZinniaValleysVoice extends CardImpl {

    private static final FilterControlledCreaturePermanent filterBuff =
            new FilterControlledCreaturePermanent("other creatures you control with base power 1");
    private static final FilterNonlandCard filterSpells =
            new FilterNonlandCard("creature spells");

    static {
        filterBuff.add(AnotherPredicate.instance);
        filterBuff.add(new BasePowerPredicate(ComparisonType.EQUAL_TO, 1));
        filterSpells.add(CardType.CREATURE.getPredicate());
    }

    public ZinniaValleysVoice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD, SubType.BARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Zinnia, Valley's Voice gets +X/+0, where X is the number of other creatures you control with base power 1.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                new PermanentsOnBattlefieldCount(filterBuff), StaticValue.get(0), Duration.WhileOnBattlefield
        )));

        // Creature spells you cast gain offspring {2} as you cast them.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledSpellsEffect(
                new ZinniaOffspringAbility("{2}"), filterSpells
        ).setText("Creature spells you cast gain offspring {2} as you cast them. " +
                "<i>(You may pay an additional {2} as you cast a creature spell. If you do, when that creature enters, create a 1/1 token copy of it.)</i>")));
    }

    private ZinniaValleysVoice(final ZinniaValleysVoice card) {
        super(card);
    }

    @Override
    public ZinniaValleysVoice copy() {
        return new ZinniaValleysVoice(this);
    }
}

class ZinniaOffspringAbility extends OffspringAbility {

    ZinniaOffspringAbility(String manaString) {
        super(manaString);
    }

    private ZinniaOffspringAbility(final ZinniaOffspringAbility ability) {
        super(ability);
    }

    @Override
    public ZinniaOffspringAbility copy() {
        return new ZinniaOffspringAbility(this);
    }

    @Override
    public void addOptionalAdditionalCosts(Ability ability, Game game) {
        if (!(ability instanceof SpellAbility)) {
            return;
        }
        Player player = game.getPlayer(ability.getControllerId());
        if (player == null) {
            return;
        }
        additionalCost.reset();
        if (!additionalCost.canPay(ability, this, ability.getControllerId(), game)
                || !player.chooseUse(Outcome.PutCreatureInPlay, "Pay " + additionalCost.getText(true) + " for offspring?", ability, game)) {
            return;
        }
        additionalCost.activate();
        ability.addCost(additionalCost.copy());
        ability.setCostsTag(OFFSPRING_ACTIVATION_VALUE_KEY, null);
        game.addDelayedTriggeredAbility(new ZinniaOffspringTriggeredAbility(), ability);
    }
}

class ZinniaOffspringTriggeredAbility extends DelayedTriggeredAbility {

    ZinniaOffspringTriggeredAbility() {
        super(new ZinniaOffspringEffect(), Duration.Custom, true);
        setTriggerPhrase("When this permanent enters, ");
    }

    private ZinniaOffspringTriggeredAbility(final ZinniaOffspringTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ZinniaOffspringTriggeredAbility copy() {
        return new ZinniaOffspringTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(getSourceId());
    }

    @Override
    public boolean isInactive(Game game) {
        return super.isInactive(game)
                || game.getStack().getSpell(getSourceId()) == null
                && game.getPermanent(getSourceId()) == null;
    }
}

class ZinniaOffspringEffect extends OneShotEffect {

    ZinniaOffspringEffect() {
        super(Outcome.Benefit);
        staticText = "create a 1/1 token copy of it";
    }

    private ZinniaOffspringEffect(final ZinniaOffspringEffect effect) {
        super(effect);
    }

    @Override
    public ZinniaOffspringEffect copy() {
        return new ZinniaOffspringEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        return permanent != null && new CreateTokenCopyTargetEffect(
                null, null, false, 1, false,
                false, null, 1, 1, false
        ).setSavedPermanent(permanent).apply(game, source);
    }
}
