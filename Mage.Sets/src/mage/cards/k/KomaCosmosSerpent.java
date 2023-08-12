package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.KomasCoilToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author weirddan455
 */
public final class KomaCosmosSerpent extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.SERPENT, "another Serpent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public KomaCosmosSerpent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // At the beginning of each upkeep, create a 3/3 blue Serpent creature token named Koma's Coil.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                Zone.BATTLEFIELD, new CreateTokenEffect(new KomasCoilToken()), TargetController.EACH_PLAYER, false, false
        ));

        // Sacrifice another Serpent: Choose one —
        // • Tap target permanent. Its activated abilities can't be activated this turn.
        Ability ability = new SimpleActivatedAbility(
                new TapTargetEffect(), new SacrificeTargetCost(new TargetControlledPermanent(filter))
        );
        ability.addEffect(new KomaCosmosSerpentEffect());
        ability.addTarget(new TargetPermanent());

        // • Koma, Cosmos Serpent gains indestructible until end of turn.
        ability.addMode(new Mode(new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn)));
        this.addAbility(ability);
    }

    private KomaCosmosSerpent(final KomaCosmosSerpent card) {
        super(card);
    }

    @Override
    public KomaCosmosSerpent copy() {
        return new KomaCosmosSerpent(this);
    }
}

class KomaCosmosSerpentEffect extends RestrictionEffect {

    public KomaCosmosSerpentEffect() {
        super(Duration.EndOfTurn);
        staticText = "Its activated abilities can't be activated this turn";
    }

    private KomaCosmosSerpentEffect(final KomaCosmosSerpentEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(getTargetPointer().getFirst(game, source));
    }

    @Override
    public boolean canUseActivatedAbilities(Permanent permanent, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public KomaCosmosSerpentEffect copy() {
        return new KomaCosmosSerpentEffect(this);
    }
}
