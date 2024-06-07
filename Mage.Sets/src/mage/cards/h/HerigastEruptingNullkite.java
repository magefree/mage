package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.ExileHandCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.abilities.keyword.EmergeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.stack.Spell;

/**
 * @author grimreap124
 */
public final class HerigastEruptingNullkite extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("creature spell");

    static {
        filter.add(CardType.CREATURE.getPredicate());
    }

    public HerigastEruptingNullkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{9}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Emerge {6}{R}{R}
        this.addAbility(new EmergeAbility(this, "{6}{R}{R}"));

        // When you cast this spell, you may exile your hand. If you do, draw three cards.
        this.addAbility(new CastSourceTriggeredAbility(new DoIfCostPaid(new DrawCardSourceControllerEffect(3), new ExileHandCost()), false));
        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Each creature spell you cast has emerge. The emerge cost is equal to its mana cost.
        this.addAbility(new SpellCastControllerTriggeredAbility(new HerigastEruptingNullkiteEffect(this), filter, false, SetTargetPointer.SPELL).setTriggerPhrase("Each creature spell you cast "));
    }

    private HerigastEruptingNullkite(final HerigastEruptingNullkite card) {
        super(card);
    }

    @Override
    public HerigastEruptingNullkite copy() {
        return new HerigastEruptingNullkite(this);
    }
}

class HerigastEruptingNullkiteEffect extends ContinuousEffectImpl {


    private int zoneChangeCounter;
    private UUID permanentId;

    HerigastEruptingNullkiteEffect(Card card) {
        super(Duration.OneUse, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "has emerge. The emerge cost is equal to its mana cost";

    }

    private HerigastEruptingNullkiteEffect(final HerigastEruptingNullkiteEffect effect) {
        super(effect);
        this.zoneChangeCounter = effect.zoneChangeCounter;
        this.permanentId = effect.permanentId;
    }

    @Override
    public HerigastEruptingNullkiteEffect copy() {
        return new HerigastEruptingNullkiteEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Spell object = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        if (object != null) {
            zoneChangeCounter = game.getState().getZoneChangeCounter(object.getSourceId()) + 1;
            permanentId = object.getSourceId();
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        if (spell != null) {
            Card card = spell.getCard();
            game.informPlayers("mana: " + spell.getManaCost().getText());
            Ability ability = new EmergeAbility(card, spell.getManaCost().getText());
            game.getState().addOtherAbility(spell.getCard(), ability, true);
        }

        return true;
    }
}