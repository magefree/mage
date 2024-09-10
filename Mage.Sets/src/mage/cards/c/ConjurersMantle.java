package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConjurersMantle extends CardImpl {

    private static final FilterCard filter
            = new FilterCard("a card that shares a creature type with that creature");

    static {
        filter.add(ConjurersMantlePredicate.instance);
    }

    public ConjurersMantle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 and has vigilance.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
                VigilanceAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has vigilance"));
        this.addAbility(ability);

        // Whenever equipped creature attacks, look at the top six cards of your library. You may reveal a card that shares a creature type with that creature from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new AttacksAttachedTriggeredAbility(new LookLibraryAndPickControllerEffect(
                6, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM
        ), AttachmentType.EQUIPMENT, false, SetTargetPointer.PERMANENT));

        // Equip {1}
        this.addAbility(new EquipAbility(1, false));
    }

    private ConjurersMantle(final ConjurersMantle card) {
        super(card);
    }

    @Override
    public ConjurersMantle copy() {
        return new ConjurersMantle(this);
    }
}

enum ConjurersMantlePredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return input
                .getSource()
                .getEffects()
                .stream()
                .map(effect -> effect.getTargetPointer().getFirst(game, input.getSource()))
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .anyMatch(permanent -> permanent.shareCreatureTypes(game, input.getObject()));
    }
}