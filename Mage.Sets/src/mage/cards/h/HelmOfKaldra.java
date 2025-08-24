package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.PermanentReferenceInCollectionPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.KaldraToken;
import mage.game.permanent.token.Token;
import mage.target.TargetPermanent;
import mage.util.RandomUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author LevelX2
 */
public final class HelmOfKaldra extends CardImpl {

    public HelmOfKaldra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has first strike, trample, and haste.
        Ability ability = new SimpleStaticAbility(new GainAbilityAttachedEffect(
                FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT
        ));
        ability.addEffect(new GainAbilityAttachedEffect(
                TrampleAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText(", trample"));
        ability.addEffect(new GainAbilityAttachedEffect(
                HasteAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText(", and haste"));
        this.addAbility(ability);

        // {1}: If you control Equipment named Helm of Kaldra, Sword of Kaldra, and Shield of Kaldra, create a legendary 4/4 colorless Avatar creature token named Kaldra and attach those Equipment to it.
        this.addAbility(new SimpleActivatedAbility(new HelmOfKaldraEffect(), new GenericManaCost(1)));

        // Equip {2}
        this.addAbility(new EquipAbility(2, false));
    }

    private HelmOfKaldra(final HelmOfKaldra card) {
        super(card);
    }

    @Override
    public HelmOfKaldra copy() {
        return new HelmOfKaldra(this);
    }
}

class HelmOfKaldraEffect extends OneShotEffect {

    private static final FilterPermanent filterHelm = new FilterControlledPermanent(SubType.EQUIPMENT);
    private static final FilterPermanent filterSword = new FilterControlledPermanent(SubType.EQUIPMENT);
    private static final FilterPermanent filterShield = new FilterControlledPermanent(SubType.EQUIPMENT);

    static {
        filterHelm.add(new NamePredicate("Helm of Kaldra"));
        filterSword.add(new NamePredicate("Sword of Kaldra"));
        filterShield.add(new NamePredicate("Shield of Kaldra"));
    }

    HelmOfKaldraEffect() {
        super(Outcome.Benefit);
        this.staticText = "if you control Equipment named Helm of Kaldra, Sword of Kaldra, and Shield of Kaldra, " +
                "create Kaldra, a legendary 4/4 colorless Avatar creature token. Attach those Equipment to it";
    }

    private HelmOfKaldraEffect(final HelmOfKaldraEffect effect) {
        super(effect);
    }

    @Override
    public HelmOfKaldraEffect copy() {
        return new HelmOfKaldraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!game.getBattlefield().contains(filterHelm, source, game, 1)
                || !game.getBattlefield().contains(filterSword, source, game, 1)
                || !game.getBattlefield().contains(filterShield, source, game, 1)) {
            return false;
        }
        Token token = new KaldraToken();
        token.putOntoBattlefield(1, game, source);
        Set<Permanent> permanents = token
                .getLastAddedTokenIds()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Permanent permanent;
        switch (permanents.size()) {
            case 0:
                return true;
            case 1:
                permanent = RandomUtil.randomFromCollection(permanents);
                break;
            default:
                FilterPermanent filter = new FilterPermanent();
                filter.add(new PermanentReferenceInCollectionPredicate(permanents, game));
                TargetPermanent target = new TargetPermanent(filter);
                target.withNotTarget(true);
                target.withChooseHint("to equip");
                Optional.ofNullable(source)
                        .map(Controllable::getControllerId)
                        .map(game::getPlayer)
                        .ifPresent(player -> player.choose(outcome, target, source, game));
                permanent = game.getPermanent(target.getFirstTarget());
        }
        if (permanent == null) {
            return true;
        }
        Set<Permanent> equipments = new HashSet<>();
        equipments.addAll(game.getBattlefield().getActivePermanents(filterHelm, source.getControllerId(), source, game));
        equipments.addAll(game.getBattlefield().getActivePermanents(filterSword, source.getControllerId(), source, game));
        equipments.addAll(game.getBattlefield().getActivePermanents(filterShield, source.getControllerId(), source, game));
        for (Permanent equipment : equipments) {
            permanent.addAttachment(equipment.getId(), source, game);
        }
        return true;
    }
}
