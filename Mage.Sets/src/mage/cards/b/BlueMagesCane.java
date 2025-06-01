package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.JobSelectAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.card.DefendingPlayerOwnsCardPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author balazskristof
 */
public final class BlueMagesCane extends CardImpl {

    private static final FilterInstantOrSorceryCard filter = new FilterInstantOrSorceryCard(
            "instant or sorcery card from defending player's graveyard"
    );

    static {
        filter.add(DefendingPlayerOwnsCardPredicate.instance);
    }

    public BlueMagesCane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        this.subtype.add(SubType.EQUIPMENT);

        // Job select
        this.addAbility(new JobSelectAbility());

        // Equipped creature gets +0/+2, is a Wizard in addition to its other types, and has "Whenever this creature attacks, exile up to one target instant or sorcery card from defending player's graveyard. If you do, copy it. You may cast the copy by paying {3} rather than paying its mana cost."
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(0, 2));
        ability.addEffect(new AddCardSubtypeAttachedEffect(SubType.WIZARD, AttachmentType.EQUIPMENT)
                .setText(", is a Wizard in addition to its other types")
        );
        Ability attackAbility = new AttacksTriggeredAbility(new BlueMagesCaneEffect());
        attackAbility.addTarget(new TargetCardInGraveyard(0, 1, filter));
        ability.addEffect(new GainAbilityAttachedEffect(attackAbility, AttachmentType.EQUIPMENT)
                .setText(", and has \"Whenever this creature attacks, exile up to one target instant or sorcery card from defending player's graveyard. "
                        + "If you do, copy it. You may cast the copy by paying {3} rather than paying its mana cost.\"")
        );
        this.addAbility(ability);

        // Spirit of the Whalaqee -- Equip {2}
        this.addAbility(new EquipAbility(2).withFlavorWord("Spirit of the Whalaqee"));
    }

    private BlueMagesCane(final BlueMagesCane card) {
        super(card);
    }

    @Override
    public BlueMagesCane copy() {
        return new BlueMagesCane(this);
    }
}

class BlueMagesCaneEffect extends OneShotEffect {

    BlueMagesCaneEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to one target instant or sorcery card from defending player's graveyard. "
                + "If you do, copy it. You may cast the copy by paying {3} rather than paying its mana cost.";
    }

    private BlueMagesCaneEffect(final BlueMagesCaneEffect effect) {
        super(effect);
    }

    @Override
    public BlueMagesCaneEffect copy() {
        return new BlueMagesCaneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (controller == null || card == null) {
            return false;
        }
        controller.moveCards(card, Zone.EXILED, source, game);
        Card copiedCard = game.copyCard(card, source, source.getControllerId());
        if (!controller.chooseUse(Outcome.Benefit, "Cast " + copiedCard.getName() + " by paying {3}?", source, game)) {
            return false;
        }
        CardUtil.castSingle(controller, source, game, copiedCard, new ManaCostsImpl<>("{3}"));
        return true;
    }
}
