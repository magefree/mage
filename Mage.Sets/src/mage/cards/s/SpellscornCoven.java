package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpellscornCoven extends AdventureCard {

    public SpellscornCoven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{3}{B}", "Take It Back", "{2}{U}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Spellscorn Coven enters the battlefield, each opponent discards a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DiscardEachPlayerEffect(TargetController.OPPONENT)));

        // Take it Back
        // Return target spell to its owner's hand.
        this.getSpellCard().getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellCard().getSpellAbility().addTarget(new TargetSpell());

        this.finalizeAdventure();
    }

    private SpellscornCoven(final SpellscornCoven card) {
        super(card);
    }

    @Override
    public SpellscornCoven copy() {
        return new SpellscornCoven(this);
    }
}
