
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.KaldraToken;

/**
 *
 * @author LevelX2
 */
public final class HelmOfKaldra extends CardImpl {

    static final FilterControlledArtifactPermanent filterHelm = new FilterControlledArtifactPermanent();
    static final FilterControlledArtifactPermanent filterShield = new FilterControlledArtifactPermanent();
    static final FilterControlledArtifactPermanent filterSword = new FilterControlledArtifactPermanent();

    static {
        filterHelm.add(new NamePredicate("Helm of Kaldra"));
        filterShield.add(new NamePredicate("Shield of Kaldra"));
        filterSword.add(new NamePredicate("Sword of Kaldra"));
    }

    public HelmOfKaldra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has first strike, trample, and haste.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT));
        Effect effect = new GainAbilityAttachedEffect(TrampleAbility.getInstance(), AttachmentType.EQUIPMENT);
        effect.setText(", trample");
        ability.addEffect(effect);
        effect = new GainAbilityAttachedEffect(HasteAbility.getInstance(), AttachmentType.EQUIPMENT);
        effect.setText(", and haste");
        ability.addEffect(effect);
        this.addAbility(ability);
        // {1}: If you control Equipment named Helm of Kaldra, Sword of Kaldra, and Shield of Kaldra, create a legendary 4/4 colorless Avatar creature token named Kaldra and attach those Equipment to it.
        this.addAbility(new ConditionalActivatedAbility(
                Zone.BATTLEFIELD,
                new HelmOfKaldraEffect(),
                new GenericManaCost(1),
                new HelmOfKaldraCondition(),
                "{1}: If you control Equipment named Helm of Kaldra, Sword of Kaldra, and Shield of Kaldra, create Kaldra, a legendary 4/4 colorless Avatar creature token. Attach those Equipment to it."));
        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.Benefit, new ManaCostsImpl<>("{2}"), false));
    }

    private HelmOfKaldra(final HelmOfKaldra card) {
        super(card);
    }

    @Override
    public HelmOfKaldra copy() {
        return new HelmOfKaldra(this);
    }
}

class HelmOfKaldraCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.getBattlefield().count(HelmOfKaldra.filterHelm, source.getControllerId(), source, game) < 1) {
            return false;
        }
        if (game.getBattlefield().count(HelmOfKaldra.filterSword, source.getControllerId(), source, game) < 1) {
            return false;
        }
        return game.getBattlefield().count(HelmOfKaldra.filterShield, source.getControllerId(), source, game) >= 1;
    }

}

class HelmOfKaldraEffect extends OneShotEffect {

    public HelmOfKaldraEffect() {
        super(Outcome.Benefit);
        this.staticText = "if you control Equipment named Helm of Kaldra, Sword of Kaldra, and Shield of Kaldra, create Kaldra, a legendary 4/4 colorless Avatar creature token. Attach those Equipment to it";
    }

    public HelmOfKaldraEffect(final HelmOfKaldraEffect effect) {
        super(effect);
    }

    @Override
    public HelmOfKaldraEffect copy() {
        return new HelmOfKaldraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (new HelmOfKaldraCondition().apply(game, source)) {
            CreateTokenEffect effect = new CreateTokenEffect(new KaldraToken());
            effect.apply(game, source);
            for (UUID tokenId : effect.getLastAddedTokenIds()) {
                Permanent kaldra = game.getPermanent(tokenId);
                if (kaldra != null) {
                    // Attach helm to the token
                    for (Permanent kaldrasHelm : game.getBattlefield().getAllActivePermanents(HelmOfKaldra.filterHelm, source.getControllerId(), game)) {
                        kaldra.addAttachment(kaldrasHelm.getId(), source, game);
                        break;
                    }
                    // Attach shield to the token
                    for (Permanent kaldrasShield : game.getBattlefield().getAllActivePermanents(HelmOfKaldra.filterShield, source.getControllerId(), game)) {
                        kaldra.addAttachment(kaldrasShield.getId(), source, game);
                        break;
                    }
                    // Attach sword to the token
                    for (Permanent kaldrasSword : game.getBattlefield().getAllActivePermanents(HelmOfKaldra.filterSword, source.getControllerId(), game)) {
                        kaldra.addAttachment(kaldrasSword.getId(), source, game);
                        break;
                    }

                }
                return true;
            }
        }
        return false;
    }
}
