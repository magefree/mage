package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.CastCardFromGraveyardThenExileItEffect;
import mage.abilities.effects.Effect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.CastFromHandWatcher;

import java.util.UUID;

/**
 * @author Rjayz
 */
public final class GaleWaterdeepProdigy extends CardImpl {

    public GaleWaterdeepProdigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);
        
        // Whenever you cast an instant or sorcery spell from your hand, you may cast up to one of the other type from your graveyard. 
        // If a spell cast from your graveyard this way would be put into your graveyard, exile it instead.
        CastCardFromGraveyardThenExileItEffect effect = new CastCardFromGraveyardThenExileItEffect();
        effect.setText("you may cast up to one of the other type from your graveyard. If a spell cast from your graveyard this way would be put into your graveyard, exile it instead.");
        this.addAbility(new GaleWaterdeepProdigyTriggeredAbility(effect,
                new FilterInstantOrSorcerySpell("an instant or sorcery spell from your hand"),
                false), new CastFromHandWatcher());

        // Choose a Background
        this.addAbility(ChooseABackgroundAbility.getInstance());
    }

    private GaleWaterdeepProdigy(final GaleWaterdeepProdigy card) {
        super(card);
    }

    @Override
    public GaleWaterdeepProdigy copy() {
        return new GaleWaterdeepProdigy(this);
    }
}

class GaleWaterdeepProdigyTriggeredAbility extends SpellCastControllerTriggeredAbility {

    public GaleWaterdeepProdigyTriggeredAbility(Effect effect, FilterSpell filter, boolean optional) {
        super(effect, filter, optional);
    }

    public GaleWaterdeepProdigyTriggeredAbility(mage.cards.g.GaleWaterdeepProdigyTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            CastFromHandWatcher watcher = game.getState().getWatcher(CastFromHandWatcher.class);
            if (watcher != null && watcher.spellWasCastFromHand(event.getSourceId())) {
                Spell spell = game.getState().getStack().getSpell(event.getSourceId());
                if (spell != null) {
                    FilterCard filterCard = new FilterCard();
                    if(spell.getCardType(game).contains(CardType.SORCERY)) {
                        filterCard.setMessage("an instant card");
                        filterCard.add(CardType.INSTANT.getPredicate());
                    } else {
                        filterCard.setMessage("a sorcery card");
                        filterCard.add(CardType.SORCERY.getPredicate());
                    }
                    this.getTargets().clear();
                    this.getTargets().add(new TargetCardInYourGraveyard(filterCard));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public mage.cards.g.GaleWaterdeepProdigyTriggeredAbility copy() {
        return new mage.cards.g.GaleWaterdeepProdigyTriggeredAbility(this);
    }
}