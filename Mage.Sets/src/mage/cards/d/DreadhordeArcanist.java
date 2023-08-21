package mage.cards.d;

import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.replacement.ThatSpellGraveyardExileReplacementEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DreadhordeArcanist extends CardImpl {

    private static final FilterCard filter = new FilterInstantOrSorceryCard(
            "instant or sorcery card with mana value less than or equal to this creature's power"
    );

    static {
        filter.add(DreadhordeArcanistPredicate.instance);
    }

    public DreadhordeArcanist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Dreadhorde Arcanist attacks, you may cast target instant or 
        // sorcery card with converted mana cost less than or equal to Dreadhorde Arcanist's 
        // power from your graveyard without paying its mana cost. If that card would be put 
        // into your graveyard this turn, exile it instead.
        Ability ability = new AttacksTriggeredAbility(new DreadhordeArcanistEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private DreadhordeArcanist(final DreadhordeArcanist card) {
        super(card);
    }

    @Override
    public DreadhordeArcanist copy() {
        return new DreadhordeArcanist(this);
    }
}

enum DreadhordeArcanistPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        Permanent sourcePermanent = input.getSource().getSourcePermanentOrLKI(game);
        return sourcePermanent != null
                && input.getObject().getManaValue() <= sourcePermanent.getPower().getValue();
    }
}

class DreadhordeArcanistEffect extends OneShotEffect {

    DreadhordeArcanistEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "you may cast target instant or sorcery card with mana value "
                + "less than or equal to {this}'s power from your graveyard without paying its mana cost. "
                + ThatSpellGraveyardExileReplacementEffect.RULE_YOUR;
    }

    private DreadhordeArcanistEffect(final DreadhordeArcanistEffect effect) {
        super(effect);
    }

    @Override
    public DreadhordeArcanistEffect copy() {
        return new DreadhordeArcanistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
        if (controller == null || card == null) {
            return false;
        }
        if (controller.chooseUse(Outcome.PlayForFree, "Cast " + card.getLogName() + '?', source, game)) {
            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
            controller.cast(controller.chooseAbilityForCast(card, game, true),
                    game, true, new ApprovingObject(source, game));
            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
            ContinuousEffect effect = new ThatSpellGraveyardExileReplacementEffect();
            effect.setTargetPointer(new FixedTarget(card, game));
            game.addEffect(effect, source);
        }
        return true;
    }
}
