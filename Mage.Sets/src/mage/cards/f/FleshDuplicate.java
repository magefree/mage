package mage.cards.f;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.keyword.VanishingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 *
 * @author Skiwkr
 */
public final class FleshDuplicate extends CardImpl {

    public FleshDuplicate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{U}");
        
        this.subtype.add(SubType.SHAPESHIFTER);
        this.subtype.add(SubType.REBEL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // You may have Flesh Duplicate enter the battlefield as a copy of any creature on the battlefield, except it has vanishing 3 if that creature doesn't have vanishing.
        this.addAbility(new EntersBattlefieldAbility(
                new CopyPermanentEffect(StaticFilters.FILTER_PERMANENT_CREATURE, new FleshDuplicateCopyApplier()),
                true
        ));
    }

    private FleshDuplicate(final FleshDuplicate card) {
        super(card);
    }

    @Override
    public FleshDuplicate copy() {
        return new FleshDuplicate(this);
    }
}

class FleshDuplicateCopyApplier extends CopyApplier {

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
        if (!blueprint.getAbilities().containsClass(VanishingAbility.class)) {
            blueprint.getAbilities().add(new VanishingAbility(3));
        }
        return true;
    }
    @Override
    public String getText() {
        return ", except it has vanishing 3 if that creature doesn't have vanishing.";
    }
}
