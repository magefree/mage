package mage.cards.g;

import mage.MageInt;
import mage.abilities.Pronoun;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class GristVoraciousLarva extends CardImpl {

    public GristVoraciousLarva(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        this.secondSideCardClazz = GristThePlagueSwarm.class;

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Grist, Voracious Larva or another creature enters the battlefield under your control, if it entered from your graveyard or was cast from your graveyard, you may pay {G}. If you do, exile Grist, then return it to the battlefield transformed under its owner's control.
        this.addAbility(new TransformAbility());
        this.addAbility(new GristVoraciousLarvaTriggeredAbility());
    }

    private GristVoraciousLarva(final GristVoraciousLarva card) {
        super(card);
    }

    @Override
    public GristVoraciousLarva copy() {
        return new GristVoraciousLarva(this);
    }
}

class GristVoraciousLarvaTriggeredAbility extends EntersBattlefieldThisOrAnotherTriggeredAbility {

    GristVoraciousLarvaTriggeredAbility() {
        super(
                new DoIfCostPaid(
                        new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED, Pronoun.IT),
                        new ManaCostsImpl<>("{G}")
                ), StaticFilters.FILTER_PERMANENT_CREATURE, false, true);
        setTriggerPhrase("Whenever {this} or another creature enters the battlefield under your control, "
                + "if it entered from your graveyard or you cast it from your graveyard, ");
    }

    private GristVoraciousLarvaTriggeredAbility(final GristVoraciousLarvaTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GristVoraciousLarvaTriggeredAbility copy() {
        return new GristVoraciousLarvaTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        EntersTheBattlefieldEvent zEvent = (EntersTheBattlefieldEvent) event;
        if (zEvent == null) {
            return false;
        }
        Permanent permanent = zEvent.getTarget();
        if (permanent == null) {
            return false;
        }
        Zone fromZone = zEvent.getFromZone();
        boolean fromGraveyard = false;
        if (fromZone == Zone.GRAVEYARD) {
            // Directly from the graveyard
            fromGraveyard = true;
        } else if (fromZone == Zone.STACK) {
            // Get spell in the stack.
            Spell spell = game.getSpellOrLKIStack(permanent.getId());
            if (spell != null && spell.getFromZone() == Zone.GRAVEYARD) {
                // Creature was cast from graveyard
                fromGraveyard = true;
            }
        }
        return fromGraveyard && super.checkTrigger(event, game);
    }
}

