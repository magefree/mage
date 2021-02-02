
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SacrificeSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.EmergeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.EldraziHorrorToken;
import mage.game.stack.Spell;

/**
 *
 * @author LevelX2
 */
public final class FoulEmissary extends CardImpl {

    private static final FilterCard filter = new FilterCard("a creature card");

    static {
        filter.add(CardType.CREATURE.getPredicate());
    }

    public FoulEmissary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Foul Emissary enters the battlefield, look at the top four cards of your library. You may reveal a creature card from among them and put it into your hand. Put the rest on the bottom of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(StaticValue.get(4), false, StaticValue.get(1), filter, false)));

        // When you sacrifice Foul Emissary while casting a spell with emerge, create a 3/2 colorless Eldrazi Horror creature token.
        this.addAbility(new FoulEmissaryTriggeredAbility(new CreateTokenEffect(new EldraziHorrorToken()), false));
    }

    private FoulEmissary(final FoulEmissary card) {
        super(card);
    }

    @Override
    public FoulEmissary copy() {
        return new FoulEmissary(this);
    }
}

class FoulEmissaryTriggeredAbility extends SacrificeSourceTriggeredAbility {

    public FoulEmissaryTriggeredAbility(Effect effect, boolean optional) {
        super(effect, optional);
    }

    public FoulEmissaryTriggeredAbility(final FoulEmissaryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            Spell spell = game.getStack().getSpell(event.getSourceId());
            return spell != null && spell.getSpellAbility() instanceof EmergeAbility;
        }
        return false;
    }

    @Override
    public FoulEmissaryTriggeredAbility copy() {
        return new FoulEmissaryTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When you sacrifice {this} while casting a spell with emerge, create a 3/2 colorless Eldrazi Horror creature token.";
    }
}
