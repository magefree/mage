package mage.cards.c;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Copycrook extends CardImpl {

    public Copycrook(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // You may have Copycrook enter the battlefield as a copy of any creature on the battlefield, except it has "Whenever this creature attacks, it connives."
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new EntersBattlefieldEffect(
                new CopyPermanentEffect(StaticFilters.FILTER_PERMANENT_CREATURE, new CopycrookApplier()),
                "you may have {this} enter the battlefield as a copy of any creature on the battlefield, " +
                        "except it has \"Whenever this creature attacks, it connives.\"", true
        )));
    }

    private Copycrook(final Copycrook card) {
        super(card);
    }

    @Override
    public Copycrook copy() {
        return new Copycrook(this);
    }
}

class CopycrookApplier extends CopyApplier {

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
        blueprint
                .getAbilities()
                .add(new AttacksTriggeredAbility(new ConniveSourceEffect())
                        .setTriggerPhrase("Whenever this creature attacks, "));
        return true;
    }
}
