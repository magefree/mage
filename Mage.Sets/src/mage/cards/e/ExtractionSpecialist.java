package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantAttackBlockTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExtractionSpecialist extends CardImpl {

    private static final FilterCard filter
            = new FilterCreatureCard("creature card with mana value 2 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public ExtractionSpecialist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When Extraction Specialist enters the battlefield, return target creature card with mana value 2 or less from your graveyard to the battlefield. That creature can't attack or block for as long as you control Extraction Specialist.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExtractionSpecialistEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private ExtractionSpecialist(final ExtractionSpecialist card) {
        super(card);
    }

    @Override
    public ExtractionSpecialist copy() {
        return new ExtractionSpecialist(this);
    }
}

class ExtractionSpecialistEffect extends OneShotEffect {

    ExtractionSpecialistEffect() {
        super(Outcome.Benefit);
        staticText = "return target creature card with mana value 2 or less from your graveyard to the battlefield. " +
                "That creature can't attack or block for as long as you control {this}";
    }

    private ExtractionSpecialistEffect(final ExtractionSpecialistEffect effect) {
        super(effect);
    }

    @Override
    public ExtractionSpecialistEffect copy() {
        return new ExtractionSpecialistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent == null
                && source.getSourcePermanentIfItStillExists(game) != null
                && source.isControlledBy(game.getControllerId(source.getSourceId()))) {
            game.addEffect(new CantAttackBlockTargetEffect(Duration.WhileControlled)
                    .setTargetPointer(new FixedTarget(permanent, game)), source);
        }
        return true;
    }
}
