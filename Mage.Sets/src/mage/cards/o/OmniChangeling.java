package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.functions.CopyApplier;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class OmniChangeling extends CardImpl {

    private static final CopyApplier applier = new CopyApplier() {
        @Override
        public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
            blueprint.getAbilities().add(new ChangelingAbility());
            return true;
        }
    };

    public OmniChangeling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Changeling
        this.addAbility(new ChangelingAbility());

        // Convoke
        this.addAbility(new ConvokeAbility());

        // You may have this creature enter as a copy of any creature on the battlefield, except it has changeling.
        this.addAbility(new SimpleStaticAbility(
            Zone.ALL,
            new EntersBattlefieldEffect(
                new CopyPermanentEffect(
                    StaticFilters.FILTER_PERMANENT_CREATURE,
                    applier
                ).setText("You may have {this} enter as a copy of any creature on the battlefield, except it has changeling"),
                "",
                true
            )
        ));
    }

    private OmniChangeling(final OmniChangeling card) {
        super(card);
    }

    @Override
    public OmniChangeling copy() {
        return new OmniChangeling(this);
    }
}
