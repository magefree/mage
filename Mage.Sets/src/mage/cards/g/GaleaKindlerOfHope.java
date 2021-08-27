package mage.cards.g;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayTheTopCardEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GaleaKindlerOfHope extends CardImpl {

    private static final FilterCard filter = new FilterCard("cast Aura and Equipment spells");

    static {
        filter.add(Predicates.or(
                SubType.AURA.getPredicate(),
                SubType.EQUIPMENT.getPredicate()
        ));
    }

    public GaleaKindlerOfHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // You may cast Aura and Equipment spells from the top of your library. When you cast an Equipment spell this way, it gains "When this Equipment enters the battlefield, attach it to target creature you control."
        Ability ability = new SimpleStaticAbility(new PlayTheTopCardEffect(filter, false));
        ability.addEffect(new InfoEffect("When you cast an Equipment spell this way, it gains " +
                "\"When this Equipment enters the battlefield, attach it to target creature you control.\""));
        this.addAbility(ability);
        this.addAbility(new GaleaKindlerOfHopeTriggeredAbility());
    }

    private GaleaKindlerOfHope(final GaleaKindlerOfHope card) {
        super(card);
    }

    @Override
    public GaleaKindlerOfHope copy() {
        return new GaleaKindlerOfHope(this);
    }
}

class GaleaKindlerOfHopeTriggeredAbility extends TriggeredAbilityImpl {

    public GaleaKindlerOfHopeTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
        this.setRuleVisible(false);
    }

    private GaleaKindlerOfHopeTriggeredAbility(final GaleaKindlerOfHopeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())
                || event.getZone() != Zone.LIBRARY
                || !event
                .getAdditionalReference()
                .getApprovingMageObjectReference()
                .refersTo(this.getSourceObject(game), game)) {
            return false;
        }
        Spell spell = game.getSpell(event.getTargetId());
        if (spell == null || !spell.hasSubtype(SubType.EQUIPMENT, game)) {
            return false;
        }
        this.getEffects().clear();
        this.addEffect(new GaleaKindlerOfHopeEffect(spell, game));
        return true;
    }

    @Override
    public GaleaKindlerOfHopeTriggeredAbility copy() {
        return new GaleaKindlerOfHopeTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When you cast an Equipment spell this way, it gains " +
                "\"When this Equipment enters the battlefield, attach it to target creature you control.\"";
    }
}

class GaleaKindlerOfHopeEffect extends ContinuousEffectImpl {

    private final MageObjectReference spellRef;
    private final MageObjectReference permRef;
    private final Ability ability = makeAbility();

    GaleaKindlerOfHopeEffect(Spell spell, Game game) {
        super(Duration.Custom, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.Benefit);
        this.spellRef = new MageObjectReference(spell, game);
        this.permRef = new MageObjectReference(spell.getMainCard(), game, 1);
    }

    private GaleaKindlerOfHopeEffect(final GaleaKindlerOfHopeEffect effect) {
        super(effect);
        this.spellRef = effect.spellRef;
        this.permRef = effect.permRef;
    }

    @Override
    public GaleaKindlerOfHopeEffect copy() {
        return new GaleaKindlerOfHopeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpell(spellRef.getSourceId());
        if (spell != null && spell.getZoneChangeCounter(game) == spellRef.getZoneChangeCounter()) {
            game.getState().addOtherAbility(spell.getCard(), ability);
            return true;
        }
        Permanent permanent = permRef.getPermanent(game);
        if (permanent == null) {
            discard();
            return false;
        }
        permanent.addAbility(ability, source.getSourceId(), game);
        return true;
    }

    private static Ability makeAbility() {
        Ability ability = new EntersBattlefieldTriggeredAbility(new AttachEffect(
                Outcome.BoostCreature, "attach it to target creature you control"
        ), false).setTriggerPhrase("When this Equipment enters the battlefield, ");
        ability.addTarget(new TargetControlledCreaturePermanent());
        return ability;
    }
}
