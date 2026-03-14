package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.MutagenToken;
import mage.players.Player;
import mage.util.CardUtil;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;


/**
 *
 * @author muz
 */
public final class MutagenManLivingOoze extends CardImpl {

    public MutagenManLivingOoze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.OOZE);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Activated abilities of artifact tokens you control cost {1} less to activate.
        this.addAbility(new SimpleStaticAbility(new MutagenManLivingOozeEffect()));

        // When Mutagen Man enters, create X Mutagen tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new MutagenToken(), GetXValue.instance)));
    }

    private MutagenManLivingOoze(final MutagenManLivingOoze card) {
        super(card);
    }

    @Override
    public MutagenManLivingOoze copy() {
        return new MutagenManLivingOoze(this);
    }
}

class MutagenManLivingOozeEffect extends CostModificationEffectImpl {

    private static final String effectText = "Activated abilities of artifact tokens you control cost {1} less to activate";

    MutagenManLivingOozeEffect() {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = effectText;
    }

    private MutagenManLivingOozeEffect(final MutagenManLivingOozeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player controller = game.getPlayer(abilityToModify.getControllerId());
        if (controller == null) {
            return false;
        }
        CardUtil.reduceCost(abilityToModify, 1);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!abilityToModify.getAbilityType().isActivatedAbility()) {
            return false;
        }
        // Activated abilities of artifact tokens you control
        Permanent permanent = game.getPermanentOrLKIBattlefield(abilityToModify.getSourceId());
        return permanent != null
            && permanent.isArtifact(game)
            && (permanent instanceof PermanentToken)
            && permanent.isControlledBy(source.getControllerId());
    }

    @Override
    public MutagenManLivingOozeEffect copy() {
        return new MutagenManLivingOozeEffect(this);
    }
}
