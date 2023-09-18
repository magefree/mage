
package mage.cards.c;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.mana.ConditionalColorlessManaAbility;
import mage.abilities.mana.builder.common.InstantOrSorcerySpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.players.Player;

/**
 * @author fireshoes
 */
public final class CuriousHomunculus extends CardImpl {

    public CuriousHomunculus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.HOMUNCULUS);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.secondSideCardClazz = mage.cards.v.VoraciousReader.class;

        // {T}: Add {C}. Spend this mana only to cast an instant or sorcery spell.
        this.addAbility(new ConditionalColorlessManaAbility(new TapSourceCost(), 1, new InstantOrSorcerySpellManaBuilder()));

        // At the beginning of your upkeep, if there are three or more instant and/or sorcery cards in your graveyard, transform Curious Homunculus.
        this.addAbility(new TransformAbility());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(), TargetController.YOU, false),
                new InstantOrSorceryCardsInControllerGraveyardCondition(3),
                "At the beginning of your upkeep, if there are three or more instant and/or sorcery cards in your graveyard, transform {this}"));
    }

    private CuriousHomunculus(final CuriousHomunculus card) {
        super(card);
    }

    @Override
    public CuriousHomunculus copy() {
        return new CuriousHomunculus(this);
    }
}

class InstantOrSorceryCardsInControllerGraveyardCondition implements Condition {

    private int value;

    public InstantOrSorceryCardsInControllerGraveyardCondition(int value) {
        this.value = value;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player p = game.getPlayer(source.getControllerId());
        if (p != null && p.getGraveyard().count(new FilterInstantOrSorceryCard(), game) >= value) {
            return true;
        }
        return false;
    }
}
