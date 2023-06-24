
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class Gigantiform extends CardImpl {

    public Gigantiform(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{G}{G}");
        this.subtype.add(SubType.AURA);

        // Kicker {4}
        this.addAbility(new KickerAbility("{4}"));

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // Enchanted creature has base power and toughness 8/8 and has trample.
        this.addAbility(new GigantiformAbility());
        // When Gigantiform enters the battlefield, if it was kicked, you may search your library for a card named Gigantiform, put it onto the battlefield, then shuffle your library.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new GigantiformEffect(), true),
                KickedCondition.ONCE,
                "When {this} enters the battlefield, if it was kicked, you may search your library for a card named Gigantiform, put it onto the battlefield, then shuffle."));
    }

    private Gigantiform(final Gigantiform card) {
        super(card);
    }

    @Override
    public Gigantiform copy() {
        return new Gigantiform(this);
    }
}

class GigantiformAbility extends StaticAbility {

    public GigantiformAbility() {
        super(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(TrampleAbility.getInstance(), AttachmentType.AURA));
        Ability ability = new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new SetBasePowerToughnessSourceEffect(8, 8, Duration.WhileOnBattlefield, SubLayer.SetPT_7b)
        );
        this.addEffect(new GainAbilityAttachedEffect(ability, AttachmentType.AURA));
    }

    public GigantiformAbility(GigantiformAbility ability) {
        super(ability);
    }

    @Override
    public GigantiformAbility copy() {
        return new GigantiformAbility(this);
    }

    @Override
    public String getRule() {
        return "Enchanted creature has base power and toughness 8/8 and has trample.";
    }
}

class GigantiformEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("card named Gigantiform");

    static {
        filter.add(new NamePredicate("Gigantiform"));
    }

    public GigantiformEffect() {
        super(Outcome.PutCardInPlay);
    }

    public GigantiformEffect(final GigantiformEffect effect) {
        super(effect);
    }

    @Override
    public GigantiformEffect copy() {
        return new GigantiformEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        if (controller != null && controller.searchLibrary(target, source, game)) {
            Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
            if (card != null) {
                controller.moveCards(card, Zone.BATTLEFIELD, source, game);
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
