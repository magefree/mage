
package mage.cards.p;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTargetTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.functions.ApplyToPermanent;

import java.util.UUID;

/**
 *
 * @author North
 */
public final class PhantasmalImage extends CardImpl {

    private static final String effectText = "a copy of any creature on the battlefield, except it's an Illusion in addition to its other types and it has \"When this creature becomes the target of a spell or ability, sacrifice it.\"";

    ApplyToPermanent phantasmalImageApplier = new ApplyToPermanent() {
        @Override
        public boolean apply(Game game, Permanent permanent, Ability source, UUID copyToObjectId) {
            if (!permanent.hasSubtype(SubType.ILLUSION, game)) {
                permanent.getSubtype(game).add(SubType.ILLUSION);
            }
            // Add directly because the created permanent is only used to copy from, so there is no need to add the ability to e.g. TriggeredAbilities
            permanent.getAbilities().add(new BecomesTargetTriggeredAbility(new SacrificeSourceEffect()));
            //permanent.addAbility(new BecomesTargetTriggeredAbility(new SacrificeSourceEffect()), game);
            return true;
        }

        @Override
        public boolean apply(Game game, MageObject mageObject, Ability source, UUID copyToObjectId) {
            if (!mageObject.hasSubtype(SubType.ILLUSION, game)) {
                mageObject.getSubtype(game).add(SubType.ILLUSION);
            }
            // Add directly because the created permanent is only used to copy from, so there is no need to add the ability to e.g. TriggeredAbilities
            mageObject.getAbilities().add(new BecomesTargetTriggeredAbility(new SacrificeSourceEffect()));
            //permanent.addAbility(new BecomesTargetTriggeredAbility(new SacrificeSourceEffect()), game);
            return true;
        }
    };

    public PhantasmalImage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.ILLUSION);

        this.color.setBlue(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // You may have Phantasmal Image enter the battlefield as a copy of any creature
        // on the battlefield, except it's an Illusion in addition to its other types and
        // it has "When this creature becomes the target of a spell or ability, sacrifice it."
        Effect effect = new CopyPermanentEffect(StaticFilters.FILTER_PERMANENT_CREATURE, phantasmalImageApplier);
        effect.setText(effectText);
        this.addAbility(new EntersBattlefieldAbility(effect, true));
    }

    public PhantasmalImage(final PhantasmalImage card) {
        super(card);
    }

    @Override
    public PhantasmalImage copy() {
        return new PhantasmalImage(this);
    }
}
