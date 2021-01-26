package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SakashimaOfAThousandFaces extends CardImpl {

    public SakashimaOfAThousandFaces(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // You may have Sakashima of a Thousand Faces enter the battlefield as a copy of another creature you control, except it has Sakashima of a Thousand Faces's other abilities.
        this.addAbility(new EntersBattlefieldAbility(new CopyPermanentEffect(
                StaticFilters.FILTER_CONTROLLED_A_CREATURE, new SakashimaOfAThousandFacesCopyApplier()
        ).setText("as a copy of another creature you control, except it has {this}'s other abilities"), true));

        // The "legend rule" doesn't apply to permanents you control.
        this.addAbility(new SimpleStaticAbility(new SakashimaOfAThousandFacesEffect()));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private SakashimaOfAThousandFaces(final SakashimaOfAThousandFaces card) {
        super(card);
    }

    @Override
    public SakashimaOfAThousandFaces copy() {
        return new SakashimaOfAThousandFaces(this);
    }
}

class SakashimaOfAThousandFacesCopyApplier extends CopyApplier {

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
        blueprint.getAbilities().add(new SimpleStaticAbility(new SakashimaOfAThousandFacesEffect()));
        blueprint.getAbilities().add(PartnerAbility.getInstance());
        return true;
    }
}

class SakashimaOfAThousandFacesEffect extends ContinuousRuleModifyingEffectImpl {

    SakashimaOfAThousandFacesEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, false, false);
        staticText = "the \"legend rule\" doesn't apply to permanents you control";
    }

    private SakashimaOfAThousandFacesEffect(final SakashimaOfAThousandFacesEffect effect) {
        super(effect);
    }

    @Override
    public SakashimaOfAThousandFacesEffect copy() {
        return new SakashimaOfAThousandFacesEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DESTROY_PERMANENT_BY_LEGENDARY_RULE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null && permanent.isControlledBy(source.getControllerId());
    }
}
