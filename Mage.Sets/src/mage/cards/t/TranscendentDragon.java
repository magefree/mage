package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TranscendentDragon extends CardImpl {

    public TranscendentDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, if you cast it, counter target spell. If that spell is countered this way, exile it instead of putting it into its owner's graveyard, then you may cast it without paying its mana cost.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TranscendentDragonEffect())
                .withInterveningIf(CastFromEverywhereSourceCondition.instance);
        ability.addTarget(new TargetSpell());
        this.addAbility(ability);
    }

    private TranscendentDragon(final TranscendentDragon card) {
        super(card);
    }

    @Override
    public TranscendentDragon copy() {
        return new TranscendentDragon(this);
    }
}

class TranscendentDragonEffect extends OneShotEffect {

    TranscendentDragonEffect() {
        super(Outcome.Benefit);
        staticText = "counter target spell. If that spell is countered this way, " +
                "exile it instead of putting it into its owner's graveyard, " +
                "then you may cast it without paying its mana cost";
    }

    private TranscendentDragonEffect(final TranscendentDragonEffect effect) {
        super(effect);
    }

    @Override
    public TranscendentDragonEffect copy() {
        return new TranscendentDragonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpell(getTargetPointer().getFirst(game, source));
        if (spell == null || !game.getStack().counter(spell.getId(), source, game, PutCards.EXILED)) {
            return false;
        }
        Player player = game.getPlayer(source.getControllerId());
        Card card = spell.getMainCard();
        if (player != null && card != null && Zone.EXILED.match(game.getState().getZone(card.getId()))) {
            CardUtil.castSpellWithAttributesForFree(player, source, game, card);
        }
        return true;
    }
}
