package mage.cards.c;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.keyword.MayhemAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChameleonMasterOfDisguise extends CardImpl {

    private static final CopyApplier applier = new CopyApplier() {
        @Override
        public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
            blueprint.setName("Chameleon, Master of Disguise");
            return true;
        }

        @Override
        public String getText() {
            return ", except his name is Chameleon, Master of Disguise";
        }
    };

    public ChameleonMasterOfDisguise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // You may have Chameleon enter as a copy of a creature you control, except his name is Chameleon, Master of Disguise.
        this.addAbility(new EntersBattlefieldAbility(new CopyPermanentEffect(
                StaticFilters.FILTER_CONTROLLED_CREATURE, applier
        ), true));

        // Mayhem {2}{U}
        this.addAbility(new MayhemAbility(this, "{2}{U}"));
    }

    private ChameleonMasterOfDisguise(final ChameleonMasterOfDisguise card) {
        super(card);
    }

    @Override
    public ChameleonMasterOfDisguise copy() {
        return new ChameleonMasterOfDisguise(this);
    }
}
